package net.marinalan.aequilibrium.transformers;

import lombok.*;
import java.util.ArrayList;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class MatchResult {
  private int numBattles;
  private Transformer winner;
  private ArrayList<Transformer> survivingLosers;
}
