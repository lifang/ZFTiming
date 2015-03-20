package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 注册
 * @author wu
 *
 */
public class RegistAction extends Action {
	
	private String ksnNo;			//硬件标识号
	private String name;			//个人姓名
	private String mobile;			//手机号
	private String password;		//登陆密码
	private String registPosition;	//注册位置
	private String appVersion;		//软件版本	
	private String product;			//软件品牌
	private File signature;			//签名文件

	public RegistAction(String ksnNo, String name, String mobile,
			String password, String registPosition, String appVersion,
			String product, File signature) {
		super();
		this.ksnNo = ksnNo;
		this.name = name;
		this.mobile = mobile;
		this.password = password;
		this.registPosition = registPosition;
		this.appVersion = appVersion;
		this.product = product;
		this.signature = signature;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("ksnNo", ksnNo);
		params.put("name", name);
		params.put("mobile", mobile);
		params.put("password", password);
		params.put("registPosition", registPosition);
		params.put("appVersion", appVersion);
		params.put("product", product);
		return params;
	}

	@Override
	protected Map<String, File> fileParams() {
		Map<String, File> fileParams = Maps.newHashMap();
		fileParams.put("signature", signature);
		return fileParams;
	}

	@Override
	public String url() {
		return "/user/register";
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return Result.class;
	}
}
