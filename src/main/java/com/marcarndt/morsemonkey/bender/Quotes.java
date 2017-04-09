package com.marcarndt.morsemonkey.bender;

import java.util.Random;

/**
 * Created by arndt on 2017/04/07.
 */
public class Quotes {

  private static String[] quotes = {
      "This is the worst kind of discrimination there is: the kind against me!",
      "I guess if you want children beaten, you have to do it yourself.",
      "Hahahahaha. Oh wait you’re serious. Let me laugh even harder.",
      "There. Now no one can say I don’t own John Larroquette’s spine.",
      "I’ll build by own theme park. With black jack, and hookers. In fact, forget the park!",
      "The game’s over. I have all the money. Compare your lives to mine and then kill yourselves!",
      "That’s closest thing to ‘Bender is great’ that anyone other me has ever said.",
      "I’m Bender, baby! Oh god, please insert liquor!",
      "Hey sexy mama, wanna kill all humans?",
      "You know what cheers me up? Other people’s misfortune.",
      "Anything less than immortality is a complete waste of time.",
      "Blackmail is such an ugly word. I prefer extortion. The ‘x’ makes it sound cool.",
      "Have you tried turning off the TV, sitting down with your children, and hitting them?",
      "Fry cracked corn and I don’t care/Leela cracked corn I still don’t care/Bender cracked corn and he is great/ Take that you stupid corn!",
      "Oh, your God!",
      "You’re a pimple on society’s ass and you’ll never amount to anything!",
      "Shut up baby, I know it!",
      "I’m so embarrassed. I wish everyone else was dead!",
      "I got ants my butt, and I needs to strut!",
      "Afterlife? If I thought I had to live another life, I’d kill myself right now!",
      "I hope he didn’t die. Unless he left a note naming me his successor, then I hope he did die.",
      "We’re making beer. I’m the brewery!",
      "Well, if jacking on will make strangers think I’m cool, I’ll do it.",
      "Oh, no room for Bender, huh? Fine! I’ll go build my own lunar lander, with blackjack and hookers. In fact, forget the lunar lander and the blackjack. Ahh, screw the whole thing!",
      "That’ll teach those other horses to take drugs.",
      "That’s what they said about being alive!",
      "Ah, Xmas Eve. Another pointless day where I accomplish nothing.",
      "O cruel fate, to be thusly boned! Ask not for whom the bone bones—it bones for thee.",
      "Honey, I wouldn’t talk about taste if I was wearing a lime green tank top.",
      "Hey, whose been messing with my radio? This isn’t alternative rock, it’s college rock.",
      "My story is a lot like yours, only more interesting cause it involves robots.",
      "“I don’t remember ever fighting Godzilla… But that is so what I would have done!",
      "We'll soon stage an attack on technology worthy of being chronicled in an anthem by Rush!",
      "Hey, the blues. The tragic sound of other people's suffering. That's kind of a pick-me-up.",
      "Morgan made me walk the Professor. There we were in the park when suddenly some old lady says I stole her purse. I chucked the Professor at her, but she kept coming. So I had to hit her with this purse I found.",
      "Who knew a cooler could also make a handy wang coffin!?",
      "I keep running people over. I'm not famous enough to get away with it.",
      "Hey! Do I preach to you when you're a lyin' stone in the gutter?? No.... So beat it!!"
  };

  public static String getRandomQuote() {
    int pos = new Random().nextInt(quotes.length);
    return quotes[pos];
  }
}
