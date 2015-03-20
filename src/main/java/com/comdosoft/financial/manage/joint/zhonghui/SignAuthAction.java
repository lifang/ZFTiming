package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 协议签名
 * @author wu
 *
 */
public class SignAuthAction extends RequireLoginAction {
	
	private File signature;

	public SignAuthAction(String phoneNum, String password,
			String position, File signature) {
		super(phoneNum, password, position);
		this.signature = signature;
	}

	@Override
	protected Map<String, File> fileParams() {
		Map<String, File> fileParams = Maps.newHashMap();
		fileParams.put("signature", signature);
		return fileParams;
	}

	@Override
	public String url() {
		return "/user/signatureAuth";
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return Result.class;
	}

}
