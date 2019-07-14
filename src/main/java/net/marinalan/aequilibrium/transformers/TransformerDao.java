package net.marinalan.aequilibrium.transformers;

import java.util.List;

public interface TransformerDao {
  public Transformer add(Transformer t);
  public List<Transformer> geAll();
  public List<Transformer> geByMultipleIds(List<Integer> ids);
  public Transformer findById(Integer id);
  public Transformer update(Integer id, Transformer t);
  public Transformer delete(Integer id);
}
