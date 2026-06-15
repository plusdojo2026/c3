package dto;

public class PreparInfo {

    private int id;                     
    // 準備情報ID（PK）

    private Integer time;               // 持ち時間
    private Integer preparTime;         // 準備時間
    private String preparItems;         // 準備項目
    private Integer setlist;            // 順番
    private String entranceMusic;       // 入場曲

    private Integer bandInfoId;         // バンド情報ID（FK）
    private Integer liveInfoId;         // ライブ情報ID（FK）

    public PreparInfo() {}

    public PreparInfo(int id, Integer time, Integer preparTime, String preparItems,
                         Integer setlist, String entranceMusic,
                         Integer bandInfoId, Integer liveInfoId) {
        this.id = id;
        this.time = time;
        this.preparTime = preparTime;
        this.preparItems = preparItems;
        this.setlist = setlist;
        this.entranceMusic = entranceMusic;
        this.bandInfoId = bandInfoId;
        this.liveInfoId = liveInfoId;
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
        return preparTime;
    }

    public void setPreparTime(Integer preparTime) {
        this.preparTime = preparTime;
    }

    public String getPreparItems() {
        return preparItems;
    }

    public void setPreparItems(String preparItems) {
        this.preparItems = preparItems;
    }

    public Integer getSetlist() {
        return setlist;
    }

    public void setSetlist(Integer setlist) {
        this.setlist = setlist;
    }

    public String getEntranceMusic() {
        return entranceMusic;
    }

    public void setEntranceMusic(String entranceMusic) {
        this.entranceMusic = entranceMusic;
    }

    public Integer getBandInfoId() {
        return bandInfoId;
    }

    public void setBandInfoId(Integer bandInfoId) {
        this.bandInfoId = bandInfoId;
    }

    public Integer getLiveInfoId() {
        return liveInfoId;
    }

    public void setLiveInfoId(Integer liveInfoId) {
        this.liveInfoId = liveInfoId;
    }
}
