package com.comdosoft.financial.timing.joint.zhonghui;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 文件上传
 * @author wu
 *
 */
public class UploadFileAction extends RequireLoginAction {
	
	private Type type;
	private InputStream photo;

	public UploadFileAction(String phoneNum, String password, String position, String appVersion,
			Type type, InputStream photo) {
		super(phoneNum, password, position, appVersion);
		this.type = type;
		this.photo = photo;
	}

	@Override
	protected Map<String, InputStream> fileParams() {
		Map<String, InputStream> fileParams = Maps.newHashMap();
		fileParams.put("photo", photo);
		return fileParams;
	}

	@Override
	public String url() {
		return MessageFormat.format("/upload/{0}", type.getName());
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return Result.class;
	}
	
	public static enum Type{
		BUSINESS("business"),
		PERSONAL("personal"),
		PERSONAL_BACK("personalBack");
		
		private Type(String name) {
			this.name = name;
		}
		private String name;
		public String getName(){
			return name;
		}
	}
}
