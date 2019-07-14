package net.marinalan.aequilibrium.transformers;

import java.util.List;

public interface TransformerService {
  public Transformer create(Transformer t);
  public List<Transformer> getAll();
  public Transformer findById(Integer id);
  public Transformer update(Integer id, Transformer t);
  public Transformer deleteById(Integer id);
  public List<Transformer> geByMultipleIds(List<Integer> ids);
  public MatchResult determineWinner(List<Integer> ids);
}
