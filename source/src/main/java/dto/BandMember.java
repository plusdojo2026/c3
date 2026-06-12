package dto;

public class BandMember {
	protected int id;
	protected String name;
	protected int partId;
	protected int bandInfoId;
	protected String partName;

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

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public BandMember(int id, String name, int partId, int bandInfoId, String partName) {
		super();
		this.id = id;
		this.name = name;
		this.partId = partId;
		this.bandInfoId = bandInfoId;
		this.partName = partName;
	}

	public BandMember(int id, String name, int partId, int bandInfoId) {
		this(id, name, partId, bandInfoId, "");
	}

	public BandMember(int id, String name, int bandInfoId, String partName) {
		this(id, name, 0, bandInfoId, partName);
	}

	public BandMember() {
		this(0, "", 0, 0);
	}
}
