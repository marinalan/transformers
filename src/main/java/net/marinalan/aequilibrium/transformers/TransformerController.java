package net.marinalan.aequilibrium.transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin( origins = "*" )
@RequestMapping(value="/bots")
public class TransformerController {

  @Autowired
  TransformerService transformerService;

  @GetMapping(value="/", headers="Accept=application/json")
  public ResponseEntity<?> getAll(){
    List<Transformer> bots = transformerService.getAll();
    return ResponseEntity.ok(bots);
  }

  @PostMapping(value="/detertmine-winner", headers="Accept=application/json")
  public ResponseEntity<MatchResult> determineWinner(@RequestBody ArrayList<Integer> ids) {
    return ResponseEntity.ok(transformerService.determineWinner(ids));
  }

  @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Transformer> getTransformerById(@PathVariable("id") int id) {
    Transformer t = transformerService.findById(id);
    if (t == null) {
      return new ResponseEntity<Transformer>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Transformer>(t, HttpStatus.OK);
  }

  @PostMapping(value="/create",headers="Accept=application/json")
  public ResponseEntity<Transformer> createTransformer(@RequestBody Transformer t, UriComponentsBuilder ucBuilder){
      System.out.println("Creating Transformer "+t);
      t = transformerService.create(t);
      //HttpHeaders headers = new HttpHeaders();
      //headers.setLocation(ucBuilder.path("/bots/{id}").buildAndExpand(t.getId()).toUri());
      return new ResponseEntity<Transformer>(t, HttpStatus.CREATED);
  }

    @DeleteMapping(value="/delete/{id}", headers ="Accept=application/json")
    public ResponseEntity<Transformer> deleteUser(@PathVariable("id") int id){
        Transformer t = transformerService.deleteById(id);
        if (t == null) {
            return new ResponseEntity<Transformer>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Transformer>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value="/update/{id}", headers="Accept=application/json")
    public ResponseEntity<Transformer> updateBot(@PathVariable("id") int id, @RequestBody Transformer currentBot){
        Transformer tExisting = transformerService.findById(id);
        if (tExisting == null) {
          return new ResponseEntity<Transformer>(HttpStatus.NOT_FOUND);
        }
        Transformer updatedBot = transformerService.update(id, currentBot);
        return new ResponseEntity<Transformer>(updatedBot, HttpStatus.OK);
    }
}
