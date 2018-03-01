package cn.damei.enumeration.wechat;
public enum QrcodeType {
    QR_SCENE("临时二维码"), QR_LIMIT_SCENE("永久二维码(场景值为整型)"), QR_LIMIT_STR_SCENE("永久二维码(场景值为字符串)");
    private String label;
    QrcodeType(String lable) {
        this.label = lable;
    }
    public String getLabel() {
        return label;
    }
}
