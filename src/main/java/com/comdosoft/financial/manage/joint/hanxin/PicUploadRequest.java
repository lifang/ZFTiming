package com.comdosoft.financial.manage.joint.hanxin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comdosoft.financial.manage.joint.JointResponse;

@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
public class PicUploadRequest extends RequestBean {
	
	public static final Logger LOG = LoggerFactory.getLogger(PicUploadRequest.class);

	public PicUploadRequest() {
		super("PicUpload");
	}
	
	private String merchantId;
	private String picType;
	private byte[] picBuffer;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	@XmlJavaTypeAdapter(value=ImageFileAdapter.class)
	public byte[] getPicBuffer() {
		return picBuffer;
	}

	public void setPicBuffer(byte[] picBuffer) {
		this.picBuffer = picBuffer;
	}
	
	public void setPic(File pic) {
		try {
			BufferedImage bi = ImageIO.read(pic);    
            ByteArrayOutputStream baos = new ByteArrayOutputStream();    
            ImageIO.write(bi, "jpg", baos);    
            this.picBuffer = baos.toByteArray(); 
		} catch (IOException e) {
			LOG.error("image file to byte[] error.",e);
		}
	}

	@Override
	public Class<? extends JointResponse> getResponseType() {
		return PicUploadResponse.class;
	}
	
	@XmlRootElement(name=RequestBean.ROOT_ELEMENT_NAME)
	public static class PicUploadResponse extends ResponseBean {
		
	}

}
