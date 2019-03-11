package com.qlangtech.tis.build.log;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gilt.logback.flume.FlumeLogstashV1Appender;
import com.qlangtech.tis.common.utils.TSearcherConfigFetcher;
import com.qlangtech.tis.indexbuilder.map.HdfsIndexBuilder;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 发送日志的时候会将当前上下文MDC“app”参数发送
 * 
 * @author 百岁
 *
 * @date 2016年8月20日
 */
public class AppnameAwareFlumeLogstashV1Appender extends FlumeLogstashV1Appender {

	public AppnameAwareFlumeLogstashV1Appender() {
		super();
		super.setFlumeAgents(TSearcherConfigFetcher.get().getLogFlumeAddress());
	}

	public void setFlumeAgents(String flumeAgents) {
		// super.setFlumeAgents(flumeAgents);
	}

	@Override
	protected Map<String, String> extractHeaders(ILoggingEvent eventObject) {
		Map<String, String> result = super.extractHeaders(eventObject);
		final Map<String, String> mdc = eventObject.getMDCPropertyMap();
		String collection = StringUtils.defaultIfEmpty(mdc.get(HdfsIndexBuilder.KEY_COLLECTION), "unknown");
		result.put(HdfsIndexBuilder.KEY_COLLECTION, collection);
		return result;
	}

}
