package net.marinalan.aequilibrium.transformers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@NamedQueries(value = {
        @NamedQuery(name="get_all_transformers", query="SELECT t FROM Transformer t"),
        @NamedQuery(name="get_list_by_ids", query="SELECT t FROM Transformer t WHERE t.id in :ids")
})
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Transformer implements Serializable {

  private static final long serialVersionUID = 8619896311396348269L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;

  @Enumerated(EnumType.STRING)
  private TransformerType type;

  private int strength;
  private int intelligence;
  private int speed;
  private int endurance;
  private int rank;
  private int courage;
  private int firepower;
  private int skill;

  public Transformer(String name, TransformerType type,
                     int strength, int intelligence, int speed, int endurance, int rank,
                     int courage, int firepower, int skill) {
    this.name = name;
    this.type = type;
    this.strength = strength;
    this.intelligence = intelligence;
    this.speed = speed;
    this.endurance = endurance;
    this.rank = rank;
    this.courage = courage;
    this.firepower = firepower;
    this.skill = skill;
  }

  public int getOverallRating(){
    return strength+intelligence+speed+endurance+firepower;
  }

  public String getPresentation(){
    return toString();
  }

  @Override
  public String toString() {
    String stats[] = {Integer.toString(strength), Integer.toString(intelligence),
            Integer.toString(speed), Integer.toString(endurance), Integer.toString(rank),
            Integer.toString(courage), Integer.toString(firepower), Integer.toString(skill)};
    String parts[] = {name, type.shortVal(), String.join(",", stats)};
    return String.join(", ", parts);
  }
}
