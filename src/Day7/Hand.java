import java.util.HashMap;

public class Hand implements Comparable<Hand> {
    String cards;
    int bet;

    public Hand(String line) {
        this.cards = line.substring(0, 5);
        this.bet = Integer.parseInt(line.substring(6).strip());
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards='" + cards + '\'' +
                ", bet=" + bet +
                '}';
    }

    /*
    Every hand is exactly one type. From strongest to weakest, they are:

    Five of a kind, where all five cards have the same label: AAAAA
    Four of a kind, where four cards have the same label and one card has a different label: AA8AA
    Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
    Three of a kind, where three cards have the same label, and the remaining two cards are each different from any
    other card in the hand: TTT98
    Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a
    third label: 23432
    One pair, where two cards share one label, and the other three cards have a different label from the pair and
    each other: A23A4
    High card, where all cards' labels are distinct: 23456
     */
    @Override
    public int compareTo(Hand o) {
        HashMap<Character, Integer> thisCards = new HashMap<>();
        HashMap<Character, Integer> otherCards = new HashMap<>();
        for (int i = 0; i < cards.length(); i++) {
            thisCards.put(cards.charAt(i),
                    thisCards.containsKey(cards.charAt(i)) ? thisCards.get(cards.charAt(i)) + 1 : 1);
        }
        for (int i = 0; i < o.cards.length(); i++) {
            otherCards.put(o.cards.charAt(i),
                    otherCards.containsKey(o.cards.charAt(i)) ? otherCards.get(o.cards.charAt(i)) + 1 : 1);
        }

        int thisScore = Score(thisCards);
        int otherScore = Score(otherCards);

        if (thisScore != otherScore) {
            return Integer.compare(thisScore, otherScore);
        }
        String cardtemp = cards;
        String othercardtemp = o.cards;
        cardtemp = cardtemp.replace('A', 'Z');
        othercardtemp = othercardtemp.replace('A', 'Z');
        cardtemp = cardtemp.replace('K', 'Y');
        othercardtemp = othercardtemp.replace('K', 'Y');
        cardtemp = cardtemp.replace('Q', 'X');
        othercardtemp = othercardtemp.replace('Q', 'X');
        cardtemp = cardtemp.replace('J', 'W');
        othercardtemp = othercardtemp.replace('J', 'W');
        cardtemp = cardtemp.replace('T', 'V');
        othercardtemp = othercardtemp.replace('T', 'V');

        for (int i = 0; i < cardtemp.length(); i++) {
            if (cardtemp.charAt(i) != othercardtemp.charAt(i)) {
                return Character.compare(cardtemp.charAt(i), othercardtemp.charAt(i));
            }
        }
        return 0;
    }

    private int Score(HashMap<Character, Integer> otherCards) {
        int otherScore;
        if (otherCards.size() == 1) {
            otherScore = 7;
        } else if (otherCards.size() == 2) {
            if (otherCards.containsValue(4)) {
                otherScore = 6;
            } else {
                otherScore = 5;
            }
        } else if (otherCards.size() == 3) {
            if (otherCards.containsValue(3)) {
                otherScore = 4;
            } else {
                otherScore = 3;
            }
        } else if (otherCards.size() == 4) {
            otherScore = 2;
        } else {
            otherScore = 1;
        }
        return otherScore;
    }
}