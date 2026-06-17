package dto;

public class EachMusic {

    // 曲ごとの情報ID（主キー）
    private int id;

    // 曲名
    private String name;

    // 演奏順番
    private Integer setlist;

    // 照明要望
    private String light_req;

    // 音響（SE）
    private String se;

    // 備考
    private String memo;

    // 準備情報ID（外部キー）
    private Integer prepar_info_id;

    public EachMusic() {}

    public EachMusic(int id, String name, Integer setlist, String light_req,
                     String se, String memo, Integer prepar_info_id) {
        this.id = id;
        this.name = name;
        this.setlist = setlist;
        this.light_req = light_req;
        this.se = se;
        this.memo = memo;
        this.prepar_info_id = prepar_info_id;
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
        return light_req;
    }

    public void setLightReq(String light_req) {
        this.light_req = light_req;
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
        return prepar_info_id;
    }

    public void setPreparInfoId(Integer prepar_info_id) {
        this.prepar_info_id = prepar_info_id;
    }
}
