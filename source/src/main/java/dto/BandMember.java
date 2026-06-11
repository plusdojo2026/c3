package dto;

public class BandMember {
	protected int id;
	protected String name;
	protected int partId;
	protected int bandInfoId;
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
	public int getPartId() {
		return partId;
	}
	public void setPartId(int partId) {
		this.partId = partId;
	}
	public int getBandInfoId() {
		return bandInfoId;
	}
	public void setBandInfoId(int bandInfoId) {
		this.bandInfoId = bandInfoId;
	}
	public BandMember(int id, String name, int partId, int bandInfoId) {
		super();
		this.id = id;
		this.name = name;
		this.partId = partId;
		this.bandInfoId = bandInfoId;
	}
	public BandMember() {
		this(0, "", 0, 0);
	}
}
