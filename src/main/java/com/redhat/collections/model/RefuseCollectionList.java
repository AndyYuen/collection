package com.redhat.collections.model;

import java.util.Objects;



import java.util.ArrayList;
import java.util.List;


public class RefuseCollectionList {

  private List<RefuseCollection> records = null;

  public RefuseCollectionList records(List<RefuseCollection> records) {
    this.records = records;
    return this;
  }

  public RefuseCollectionList addRecordsItem(RefuseCollection recordsItem) {
    if (this.records == null) {
      this.records = new ArrayList<RefuseCollection>();
    }
    this.records.add(recordsItem);
    return this;
  }

   /**
   * Matching refuse collection records.
   * @return records
  **/

  public List<RefuseCollection> getRecords() {
    return records;
  }

  public void setRecords(List<RefuseCollection> records) {
    this.records = records;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RefuseCollectionList refuseCollectionList = (RefuseCollectionList) o;
    return Objects.equals(this.records, refuseCollectionList.records);
  }

  @Override
  public int hashCode() {
    return Objects.hash(records);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RefuseCollectionList {\n");
    
    sb.append("    records: ").append(toIndentedString(records)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
