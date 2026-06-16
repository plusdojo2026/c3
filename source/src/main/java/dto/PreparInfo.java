package dto;

public class PreparInfo {

    // 準備情報ID（主キー）
    private int id;

    // 持ち時間
    private Integer time;

    // 準備時間
    private Integer prepar_time;

    // 準備項目
    private String prepar_items;

    // 順番
    private Integer setlist;

    // 入場曲
    private String entrance_music;

    // バンド情報ID（外部キー）
    private Integer band_info_id;

    // ライブ情報ID（外部キー）
    private Integer live_info_id;

    public PreparInfo() {}

    public PreparInfo(int id, Integer time, Integer prepar_time, String prepar_items,
                         Integer setlist, String entrance_music,
                         Integer band_info_id, Integer live_info_id) {
        this.id = id;
        this.time = time;
        this.prepar_time = prepar_time;
        this.prepar_items = prepar_items;
        this.setlist = setlist;
        this.entrance_music = entrance_music;
        this.band_info_id = band_info_id;
        this.live_info_id = live_info_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getPreparTime() {
        return prepar_time;
    }

    public void setPreparTime(Integer prepar_time) {
        this.prepar_time = prepar_time;
    }

    public String getPreparItems() {
        return prepar_items;
    }

    public void setPreparItems(String prepar_items) {
        this.prepar_items = prepar_items;
    }

    public Integer getSetlist() {
        return setlist;
    }

    public void setSetlist(Integer setlist) {
        this.setlist = setlist;
    }

    public String getEntranceMusic() {
        return entrance_music;
    }

    public void setEntranceMusic(String entrance_music) {
        this.entrance_music = entrance_music;
    }

    public Integer getBandInfoId() {
        return band_info_id;
    }

    public void setBandInfoId(Integer band_info_id) {
        this.band_info_id = band_info_id;
    }

    public Integer getLiveInfoId() {
        return live_info_id;
    }

    public void setLiveInfoId(Integer live_info_id) {
        this.live_info_id = live_info_id;
    }
}
