package cn.damei.dto.message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
public class TemplateMessage implements Serializable ,Cloneable{
    private static final long serialVersionUID = -1149514807672996439L;
    @JsonProperty(value = "touser")
    private String toUser;
    @JsonProperty(value = "template_id")
    private String templateId;
    private String url;
    @JsonIgnore
    private NameValue head;
    @JsonIgnore
    private NameValue tail;
    @JsonProperty(value = "data")
    private Map<String, NameValue> content;
    private final static String HEAD_KEY = "first";
    private final static String TAIL_KEY = "remark";
    private final static String DEFAULT_COLOR = "#173177";
    public TemplateMessage() {
        this.content = new HashMap<>();
    }
    public TemplateMessage clone() {
        try {
            return (TemplateMessage) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public TemplateMessage(String toUser, String templateId, String url, Map<String, NameValue> content) {
        this.toUser = toUser;
        this.templateId = templateId;
        this.url = url;
        this.content = content;
    }
    public String getToUser() {
        return toUser;
    }
    public TemplateMessage setToUser(String toUser) {
        this.toUser = toUser;
        return this;
    }
    public String getTemplateId() {
        return templateId;
    }
    public TemplateMessage setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }
    public String getUrl() {
        return url;
    }
    public TemplateMessage setUrl(String url) {
        this.url = url;
        return this;
    }
    public Map<String, NameValue> getContent() {
        return content;
    }
    public void setContent(Map<String, NameValue> content) {
        this.content = content;
    }
    public NameValue getHead() {
        return head;
    }
    public TemplateMessage pushHead(String value) {
        return pushHead("#FF0000", value);
    }
    public TemplateMessage pushHead(String color, String value) {
        NameValue head = new NameValue(color, value);
        this.head = head;
        content.put(HEAD_KEY, head);
        return this;
    }
    public NameValue getTail() {
        return tail;
    }
    public TemplateMessage pushTail(String value) {
        return pushTail(DEFAULT_COLOR, value);
    }
    public TemplateMessage pushTail(String color, String value) {
        NameValue tail = new NameValue(color, value);
        this.tail = tail;
        content.put(TAIL_KEY, tail);
        return this;
    }
    public void setTail(NameValue tail) {
        this.tail = tail;
    }
    public TemplateMessage pushItem(String key, String value) {
        return pushItem(key, DEFAULT_COLOR, value);
    }
    public TemplateMessage pushItem(String key, String color, String value) {
        content.put(key, new NameValue(color, value));
        return this;
    }
    public static class NameValue implements Serializable {
        private String name;
        private String value;
        private static final long serialVersionUID = 6752792351484246184L;
        public NameValue() {
        }
        public NameValue(String name, String value) {
            this.name = name;
            this.value = value;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public String toString() {
            return "NameValue[name=" + name + ",value" + value + "]";
        }
    }
}
