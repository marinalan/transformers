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
  public List<Transformer> getRankedAutobotsWithIds(List<Integer> ids) {
    return transformerDao.getRankedAutobotsWithIds(ids);
  }

  @Override
  public List<Transformer> getRankedDecepticonsWithIds(List<Integer> ids) {
    return transformerDao.getRankedDecepticonsWithIds(ids);
  }

  private Transformer getWinnerAmongSurvivors(ArrayList<Transformer> survivors) {
    if(survivors.size() == 0)
      return null;

    Transformer winner = survivors.get(0);
    int highestOverallRating = winner.getOverallRating();
    for (int i=1; i<survivors.size(); i++) {
      Transformer curMember = survivors.get(i);
      if (curMember.getOverallRating() > highestOverallRating) {
        winner = curMember;
        highestOverallRating = winner.getOverallRating();
      }
    }
    return winner;
  }

  @Override
  public MatchResult determineWinner(List<Integer> ids) {
    MatchResult mr = new MatchResult();
    List<Transformer> autobots = getRankedAutobotsWithIds(ids);
    List<Transformer> decepticons = getRankedDecepticonsWithIds(ids);

    int numA = autobots.size();
    int numD = decepticons.size();
    int numBattles = 0;

    ArrayList<Transformer> autobotsSurvivors = new ArrayList<Transformer>();
    ArrayList<Transformer> decepticonSurvivors = new ArrayList<Transformer>();

    int minTeamNum = numA < numD ? numA : numD;

    for (int j=0; j<minTeamNum; j++) {
      numBattles++;
      Transformer a = autobots.get(j);
      Transformer b = decepticons.get(j);
      int outcome = a.matchOutcome(b);
      switch (outcome){
        case -2:
          autobotsSurvivors.clear();
          decepticonSurvivors.clear();
          j = minTeamNum+2;  // to cause break from loop
          break;
        case 1:
          autobotsSurvivors.add(a);
          break;
        case -1:
          decepticonSurvivors.add(b);
          break;
        case 0:
          break; // both die, so no adding to survivors
      }
    }
    ArrayList<Transformer> winningTeam = null;
    ArrayList<Transformer> losingTeam = null;
    if ((minTeamNum - autobotsSurvivors.size()) > minTeamNum - decepticonSurvivors.size()) {
      losingTeam = autobotsSurvivors;
      winningTeam = decepticonSurvivors;
    } else if((minTeamNum - autobotsSurvivors.size()) < minTeamNum - decepticonSurvivors.size()) {
      winningTeam = autobotsSurvivors;
      losingTeam = decepticonSurvivors;
    }
    Transformer winner = null;
    int highestOverallRating = 0;
    if (winningTeam != null && winningTeam.size() > 0) {
      // find with highest overall rating within winning team
      winner = getWinnerAmongSurvivors(winningTeam);
    } else {
      // what to do if both team have the same losses?
      // just look who from survivors has highest overall rating?
      if (autobotsSurvivors.size() + decepticonSurvivors.size() > 0){
          ArrayList<Transformer> survivors = new ArrayList<>(autobotsSurvivors);
          survivors.addAll((decepticonSurvivors));
          winner = getWinnerAmongSurvivors(survivors);
      } else {
          // no winners
          winner = null;
      }
    }
    mr.setNumBattles(numBattles);
    mr.setWinner(winner);

    ArrayList<Transformer> losingSurvivors = new ArrayList<Transformer>();
    if (winner != null) {
      if(winner.getType() == TransformerType.Autobot){
        losingSurvivors.addAll(decepticonSurvivors);
        if(numD > minTeamNum){
          for(int i = minTeamNum; i<numD; i++) {
            losingSurvivors.add(decepticons.get(i));
          }
        }
      } else {
        losingSurvivors.addAll(autobotsSurvivors);
        if(numA > minTeamNum){
          for(int i = minTeamNum; i<numA; i++) {
            losingSurvivors.add(autobots.get(i));
          }
        }
      }
    }
    mr.setSurvivingLosers(losingSurvivors);
    return mr;
  }
}
