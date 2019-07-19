package net.marinalan.aequilibrium.transformers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@NamedQueries(value = {
  @NamedQuery(name="get_all_transformers", query="SELECT t FROM Transformer t"),
  @NamedQuery(name="get_list_by_ids", query="SELECT t FROM Transformer t WHERE t.id in :ids"),
  @NamedQuery(name="get_ranked_autobots_by_ids",
    query="SELECT t FROM Transformer t WHERE t.id in :ids AND t.type=net.marinalan.aequilibrium.transformers.TransformerType.Autobot ORDER By rank, id"),
  @NamedQuery(name="get_ranked_decepticons_by_id",
    query="SELECT t FROM Transformer t WHERE t.id in :ids AND t.type=net.marinalan.aequilibrium.transformers.TransformerType.Decepticon ORDER BY rank, id")
})
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Transformer implements Serializable {

  private static final long serialVersionUID = 8619896311396348269L;

  @Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  /* may be similar to compareTo, but for the sake to determine match outcome between autobot and decepticon
   * 1 - winner,  oppenent is loser
   * -1 - loser, opponent is winner
   * 0 - tie, both die
   * -2 - "Optimus Prime|Predaking" meets "Optimus Prime|Predaking", all competitors destroyed
   */
  public int matchOutcome(Transformer opponent) {
    if (type.equals(opponent.getType())) {
      throw new IllegalArgumentException("Fight with not of your own kindyour own kind is forbidden!");
    }
    if(name.equals("Optimus Prime") || name.equals("Predaking")) {
      if(opponent.getName().equals("Optimus Prime") || opponent.getName().equals("Predaking")) {
        return -2; // all annihilated
      } else {
        return 1; //wins
      }
    }
    if(opponent.getName().equals("Optimus Prime") || opponent.getName().equals("Predaking")) {
      return -1; // loses
    }
    if( (opponent.getCourage() - courage >= 4) || (opponent.getStrength() - strength >= 3) ) {
      return -1; //loses
    } else if( (courage - opponent.getCourage() >= 4) || (strength - opponent.getStrength() >= 3) ) {
      return 1; //wins
    }
    if(skill - opponent.getSkill() >= 3) {
      return 1; // wins
    } else if(opponent.getSkill() - skill >= 3) {
      return -1; //loses
    }
    if (getOverallRating() > opponent.getOverallRating()) {
      return 1; //wins
    } else if ( opponent.getOverallRating() > getOverallRating() ) {
      return -1; //loses
    }
    return 0;
  }
}
