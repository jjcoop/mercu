package mercury.salems.antiCorruptionLayer.sharedModel;

public class PartSimple {

  private Long id;
  private String partName;
  private String partDescription;

  public PartSimple(Long id, String partName, String partDescription) {
    this.id = id;
    this.partName = partName;
    this.partDescription = partDescription;
  }

  public Long getId() {
    return id;
  }

  public String getPartName() {
    return partName;
  }

  public String getPartDescription() {
    return partDescription;
  }
}
