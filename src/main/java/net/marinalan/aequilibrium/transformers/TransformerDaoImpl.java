package net.marinalan.aequilibrium.transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TransformerDaoImpl  implements TransformerDao{
  @Autowired
  EntityManager em;

  @Override
  public Transformer add(Transformer t) {
    em.persist(t);
    return t;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Transformer> geAll() {
    return em.createNamedQuery("get_all_transformers").getResultList();
  }

  @Override
  public List<Transformer> geByMultipleIds(List<Integer> ids) {
    Query query = em.createNamedQuery("get_list_by_ids");
    query.setParameter("ids", ids);
    return query.getResultList();
  }

  @Override
  public Transformer findById(Integer id) {
    return em.find(Transformer.class, id);
  }

  @Override
  public Transformer update(Integer id, Transformer updatedValue) {
    em.merge(updatedValue);
    return updatedValue;
  }

  @Override
  public Transformer delete(Integer id) {
    Transformer t = findById(id);
    em.remove(t);
    return t;
  }

  public void playWithEntityManager() {
    Transformer t1 = new Transformer("Soundwave", TransformerType.Decepticon, 8,9,2,6,7,5,6,10);
    Transformer t2 = new Transformer("Bluestreak", TransformerType.Autobot, 6,6,7,9,5,2,9,7);
    Transformer t3 = new Transformer("Hubcap", TransformerType.Autobot, 4,4,4,4,4,4,4,4);
    Transformer t4 = new Transformer("Optimus Prime", TransformerType.Autobot, 6,7,8,7,6,5,4,3);
    Transformer t5 = new Transformer("Predaking", TransformerType.Decepticon, 4,4,4,4,4,4,4,4);
    Transformer t6 = new Transformer("Bender", TransformerType.Decepticon, 8,5,7,6,5,3,8,5);

    em.persist(t1);
    em.persist(t2);
    em.persist(t3);
    em.persist(t4);
    em.persist(t5);
    em.persist(t6);
  }
}
