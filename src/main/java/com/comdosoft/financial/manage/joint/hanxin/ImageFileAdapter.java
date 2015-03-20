package com.comdosoft.financial.manage.joint.hanxin;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.codec.binary.Base64;

public class ImageFileAdapter extends XmlAdapter<String, byte[]> {

	@Override
	public byte[] unmarshal(String v) throws Exception {
		return Base64.decodeBase64(v);
	}

	@Override
	public String marshal(byte[] v) throws Exception {
		return Base64.encodeBase64String(v);
	}

}
