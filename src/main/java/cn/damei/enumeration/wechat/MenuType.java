package cn.damei.enumeration.wechat;
public enum MenuType {
    CLICK("点击推事件", "click"),
    VIEW("跳转URL", "view"),
    SCANCODE_PUSH("扫码推事件", "scancode_push"),
    SCANCODE_WAITMSG("扫码推事件且弹出“消息接收中”提示框", "scancode_waitmsg"),
    PIC_SYSPHOTO("弹出系统拍照发图", "pic_sysphoto"),
    PIC_PHOTO_OR_ALBUM("弹出拍照或者相册发图", "pic_photo_or_album"),
    PIC_WEIXIN("弹出微信相册发图器", "pic_weixin"),
    LOCATION_SELECT("弹出地理位置选择器", "location_select"),
    MEDIA_ID("下发消息（除文本消息）", "media_id"),
    VIEW_LIMITED("跳转图文消息URL", "view_limited");
    private String label;
    private String value;
    MenuType(String label, String value) {
        this.label = label;
        this.value = value;
    }
    public String getLabel() {
        return label;
    }
    public String getValue() {
        return value;
    }
}
