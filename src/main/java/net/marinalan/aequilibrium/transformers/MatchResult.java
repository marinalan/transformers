package net.marinalan.aequilibrium.transformers;

import lombok.*;
import java.util.ArrayList;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class MatchResult {
  private int numBattles;
  private Transformer winner;
  private ArrayList<Transformer> survivingLosers;


  public String getPresentation(){
    return toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(numBattles);
    sb.append(" battle"+ (numBattles>1 ? "s" : ""));
    sb.append("\n");
    sb.append(("Winning team ("));
    if(winner != null) {
      sb.append( (winner.getType() == TransformerType.Autobot) ? "Autobots" : "Decepticons" );
      sb.append("): ");
      sb.append(winner.getName());
    } else {
      sb.append("none)");
    }
    sb.append("\n");
    if (survivingLosers.size()>0) {
      sb.append("Survivors from the losing team (");
      sb.append( (survivingLosers.get(0).getType() == TransformerType.Autobot) ? "Autobots" : "Decepticons" );
      sb.append("): ");
      boolean first = true;
      for(Transformer t : survivingLosers) {
        if(!first) sb.append(", ");
        sb.append(t.getName());
        first = false;
      }
    } else {
      sb.append("Survivors from the losing team: none");
    }
    return sb.toString();
   /*
    return "MatchResult{" +
            "numBattles=" + numBattles +
            ", winner=" + winner +
            ", survivingLosers=" + survivingLosers +
            '}';
    */
  }
}
