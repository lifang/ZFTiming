package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
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
	private File photo;

	public UploadFileAction(String phoneNum, String password, String position,
			Type type, File photo) {
		super(phoneNum, password, position);
		this.type = type;
		this.photo = photo;
	}

	@Override
	protected Map<String, File> fileParams() {
		Map<String, File> fileParams = Maps.newHashMap();
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
