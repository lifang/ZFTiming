package com.comdosoft.financial.manage.joint.hanxin;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.joint.JointRequest;
import com.comdosoft.financial.manage.joint.JointResponse;
import com.comdosoft.financial.manage.utils.HttpUtils;
import com.comdosoft.financial.manage.utils.StringUtils;

/**
 * requst 对象
 * @author wu
 *
 */
public abstract class RequestBean implements JointRequest,ResponseHandler<JointResponse> {
	
	private static final Logger LOG = LoggerFactory.getLogger(RequestBean.class);
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final RequestSeq REQUEST_SEQ = new RequestSeq();
	static final String ROOT_ELEMENT_NAME = "bppos";
	
	private String desKey;
	
	private String application;
	private String version;
	private String sendSeqId;
	private String sendTime;
	private String terminalId;
	
	public RequestBean(String application){
		setApplication(application+".Req");
		setVersion("1.0.0");
		setSendTime(DATE_TIME_FORMAT.format(LocalDateTime.now()));
		setSendSeqId(String.format("%06d", REQUEST_SEQ.getNextSeq()));
	}

	@XmlAttribute
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	
	@XmlAttribute
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@XmlAttribute
	public String getSendSeqId() {
		return sendSeqId;
	}
	public void setSendSeqId(String sendSeqId) {
		this.sendSeqId = sendSeqId;
	}
	
	@XmlAttribute
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getDesKey() {
		return desKey;
	}
	
	/**
	 * 生成请求数据
	 * @param manager
	 * @return
	 * @throws Exception
	 */
	public String generateBody(ActionManager manager) throws Exception{
		desKey = StringUtils.randomString(32);
		String des3desKey=RSACoder.encryptByPublicKey(desKey,manager.getRsaKey());
		String baseTerminal=Base64.encodeBase64String(getTerminalId().getBytes());
		byte[] desContent=DesUtil.encrypt(toString().getBytes(),desKey.getBytes());
        return baseTerminal+"|"+des3desKey+"|"+Base64.encodeBase64String(desContent);
	}

	@Override
	public JointResponse handleResponse(HttpResponse res) throws IOException {
		final StatusLine statusLine = res.getStatusLine();
        final HttpEntity entity = res.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
        String resString = EntityUtils.toString(entity,HttpUtils.DEFAULT_CHARSET);
        LOG.debug(resString);
        return ResponseBean.parseBody(resString, this);
	}

	@Override
	public String toString() {
		StringWriter writer = new StringWriter();
		JAXB.marshal(this, writer);
		return writer.toString();
	}

	/**
	 * 请求序列id生成器
	 * @author wu
	 *
	 */
	private static class RequestSeq {
		private AtomicInteger index;
		private LocalDate date;

		public RequestSeq() {
			int i = new Random().nextInt(90000);
			index = new AtomicInteger(i);
			date = LocalDate.now();
		}

		public int getNextSeq(){
			if(!LocalDate.now().isEqual(date)){
				date = LocalDate.now();
				index.set(1);
			}
			return index.getAndIncrement();
		}

	}
	
}
