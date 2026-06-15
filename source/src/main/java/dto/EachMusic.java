package dto;

public class EachMusic {

    // 曲ごとの情報ID（主キー）
    private int id;

    // 曲名
    private String name;

    // 演奏順番
    private Integer setlist;

    // 照明要望
    private String lightReq;

    // 音響（SE）
    private String se;

    // 備考
    private String memo;

    // 準備情報ID（外部キー）
    private Integer preparInfoId;

    public EachMusic() {}

    public EachMusic(int id, String name, Integer setlist, String lightReq, String se, String memo, Integer preparInfoId) {
        this.id = id;
        this.name = name;
        this.setlist = setlist;
        this.lightReq = lightReq;
        this.se = se;
        this.memo = memo;
        this.preparInfoId = preparInfoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSetlist() {
        return setlist;
    }

    public void setSetlist(Integer setlist) {
        this.setlist = setlist;
    }

    public String getLightReq() {
        return lightReq;
    }

    public void setLightReq(String lightReq) {
        this.lightReq = lightReq;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getPreparInfoId() {
        return preparInfoId;
    }

    public void setPreparInfoId(Integer preparInfoId) {
        this.preparInfoId = preparInfoId;
    }
}

