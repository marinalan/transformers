package net.marinalan.aequilibrium.transformers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransformerServiceImpl  implements TransformerService {
  @Autowired
  TransformerDao transformerDao;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public Transformer create(Transformer t) {
    return transformerDao.add(t);
  }

  @Override
  public List<Transformer> getAll() {
    return transformerDao.geAll();
  }

  @Override
  public Transformer findById(Integer id) {
    return transformerDao.findById(id);
  }

  @Override
  public Transformer update(Integer id, Transformer t) {
    return transformerDao.update(id, t);
  }

  @Override
  public Transformer deleteById(Integer id) {
    return transformerDao.delete(id);
  }

  @Override
  public List<Transformer> geByMultipleIds(List<Integer> ids) {
    return transformerDao.geByMultipleIds(ids);
  }

  @Override
  public MatchResult determineWinner(List<Integer> ids) {
    MatchResult mr = new MatchResult();
    List<Transformer> bots = geByMultipleIds(ids);

    List<Transformer> a_team = new ArrayList<Transformer>();
    List<Transformer> d_team = new ArrayList<Transformer>();

    for(Transformer t: bots) {
      if(t.getType() == TransformerType.Autobot) {
        a_team.add(t);
      } else {
        d_team.add(t);
      }
    }

    // sort by rank

    //Collections.sort()
    return mr;
  }
}
