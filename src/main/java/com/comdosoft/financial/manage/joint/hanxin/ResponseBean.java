package com.comdosoft.financial.manage.joint.hanxin;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.joint.JointResponse;

/**
 * response 对象
 * @author wu
 *
 */
public class ResponseBean implements JointResponse {
	
	public static final Logger LOG = LoggerFactory.getLogger(ResponseBean.class);
	
	private int result;
	private String application;
	private String version;
	private String respCode;
	private String respDesc;
	private String terminalId;

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
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	@Override
	public int getResult() {
		return result;
	}
	
	@Override
	public boolean isSuccess() {
		return getResult()==RESULT_SUCCESS;
	}
	
	@Override
	public String getMsg() {
		return getRespDesc();
	}
	
	/**
	 * 解析返回数据
	 * @param body
	 * @param request
	 * @return
	 */
	public static JointResponse parseBody(String body,RequestBean request){
		String[] resDataArr = body.trim().split("\\|");
		LOG.debug("resDataArr length......{}",resDataArr.length);
		String code = null;
		String msg = null;
		String content = null;
		switch(resDataArr.length){
		case 3:
			content = resDataArr[2];
		case 2:
			msg = resDataArr[1];
		case 1:
			code = resDataArr[0];
		}
		JointResponse bean = null;
		if("0".equals(code)){
			bean = parseFalse(msg, content);
		}else if("1".equals(code)){
			bean = parseSuccess(msg, content,request);
		}
		return bean;
	}
	
	private static ResponseBean parseFalse(String msg,String content){
		ResponseBean bean = new ResponseBean();
		bean.result = RESULT_FAIL;
		bean.respCode = msg;
		byte[] bytes = Base64.decodeBase64(content);
		bean.respDesc = StringUtils.newStringUtf8(bytes);
		return bean;
	}
	
	private static JointResponse parseSuccess(String msg,String content,RequestBean request){
		byte[] desResData = Base64.decodeBase64(msg);//3DES(报文)
		byte[] respCheckValue = Base64.decodeBase64(content);//MD5(报文)
		try {
			byte[] resDataByte = DesUtil.decrypt(desResData,request.getDesKey().getBytes("UTF-8"));
			byte[] checkValue = DigestUtils.md5(resDataByte);
			if(Arrays.equals(respCheckValue, checkValue)){
				String resData = StringUtils.newStringUtf8(resDataByte);
				LOG.debug("resp string .....  {}",resData);
				Reader reader = new StringReader(resData);
				JointResponse resp = JAXB.unmarshal(reader, request.getResponseType());
				resp.setResult(RESULT_SUCCESS);
				return resp;
			}
		} catch (Exception e) {
			LOG.error("",e);
		}
		ResponseBean bean = new ResponseBean();
		bean.result = RESULT_FAIL;
		bean.respCode = msg;
		bean.respDesc = "消息解析出错！";
		return bean;
	}
	@Override
	public void setResult(int result) {
		this.result = result;
	}
}
