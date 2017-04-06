package com.louie.trace;

import static com.github.kristofa.brave.IdConversion.convertToLong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.ServerRequestAdapter;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.TraceData;
import com.github.kristofa.brave.http.BraveHttpHeaders;
import com.github.kristofa.brave.http.HttpServerRequest;
import com.github.kristofa.brave.http.SpanNameProvider;

public class CusHttpServerRequestAdapter  implements ServerRequestAdapter {
    private final HttpServerRequest request;
    private final SpanNameProvider spanNameProvider;
    private final String service;
    private final String data;

    public CusHttpServerRequestAdapter(HttpServerRequest request,String service,String data, SpanNameProvider spanNameProvider) {
        this.request = request;
        this.spanNameProvider = spanNameProvider;
        this.service = service;
        this.data = data;
    }

    public TraceData getTraceData() {
        String sampled = request.getHttpHeaderValue(BraveHttpHeaders.Sampled.getName());
        String parentSpanId = request.getHttpHeaderValue(BraveHttpHeaders.ParentSpanId.getName());
        String traceId = request.getHttpHeaderValue(BraveHttpHeaders.TraceId.getName());
        String spanId = request.getHttpHeaderValue(BraveHttpHeaders.SpanId.getName());

        // Official sampled value is 1, though some old instrumentation send true
        Boolean parsedSampled = sampled != null
            ? sampled.equals("1") || sampled.equalsIgnoreCase("true")
            : null;

        if (traceId != null && spanId != null) {
            return TraceData.create(getSpanId(traceId, spanId, parentSpanId, parsedSampled));
        } else if (parsedSampled == null) {
            return TraceData.EMPTY;
        } else if (parsedSampled.booleanValue()) {
            // Invalid: The caller requests the trace to be sampled, but didn't pass IDs
            return TraceData.EMPTY;
        } else {
            return TraceData.NOT_SAMPLED;
        }
    }

    public String getSpanName() {
        return spanNameProvider.spanName(request);
    }

    public Collection<KeyValueAnnotation> requestAnnotations() {
        KeyValueAnnotation sera = KeyValueAnnotation.create(
                "service", service);
        KeyValueAnnotation dataa = KeyValueAnnotation.create(
                "data", data);
        List<KeyValueAnnotation> kas = new ArrayList<KeyValueAnnotation>();
        kas.add(sera);
        kas.add(dataa);
        return kas;
        //return Collections..singleton(sera);
    }

    static SpanId getSpanId(String traceId, String spanId, String parentSpanId, Boolean sampled) {
        return SpanId.builder()
            .traceIdHigh(traceId.length() == 32 ? convertToLong(traceId, 0) : 0)
            .traceId(convertToLong(traceId))
            .spanId(convertToLong(spanId))
            .sampled(sampled)
            .parentId(parentSpanId == null ? null : convertToLong(parentSpanId)).build();
   }
}

