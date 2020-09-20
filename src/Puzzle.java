import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class Puzzle {

  Node currentNode;
  int maxNodes;
  char[][] goalState = new char[][]{{'0','1','2'},{'3','4','5'},{'6','7','8'}};
  ArrayList<Node> frontier = new ArrayList<>();

  public Puzzle(Node startNode) {

      currentNode = startNode;
      maxNodes = 1000;
  }

  public void printState() { this.currentNode.printState();
  }

  public void setMaxMoves(int x){ this.maxNodes = x;
  }

  public void move(String s) {

      switch(s) {
            case "up":
                currentNode = currentNode.move(0);
                break;
            case "down":
                currentNode = currentNode.move(1);
                break;
            case "left":
                currentNode = currentNode.move(2);
                break;
            case "right":
                currentNode = currentNode.move(3);
                break;
            default:
                break;
        }
    }

  public void setState(String s1, String s2, String s3) {

      char[][] newBoard = new char[3][3];
      newBoard[0] = s1.toCharArray();
      newBoard[1] = s2.toCharArray();
      newBoard[2] = s3.toCharArray();

      for(int i = 0; i < 3; i++) {
          for(int j = 0; j < 3; j++) {
              if(newBoard[i][j] == ('b')) {
                  newBoard[i][j] = '0';
              }
          }
      }
      currentNode.setBoard(newBoard);
  }

  public void randomizeState(int n){

      for(int i = 0; i < n; i++) {
          currentNode = currentNode.move(ThreadLocalRandom.current().nextInt(0, 4));
      }
  }

  public void solveA(int h) {

      int i = 0;
      while( (!(Arrays.deepEquals(currentNode.getBoard(), goalState)) && (i < maxNodes) ) ){

          this.expand();
          switch (h) {

              case 1:
                  frontier.sort(Comparator.comparing(Node::getF1));
                  break;
              case 2:
                  frontier.sort(Comparator.comparing(Node::getF2));
                  break;
              default:
                  break;
          }
          i++;
          currentNode = frontier.get(0);
      }
      this.printState();
  }

  public void solveBeam(int k) {

      this.expand();
      int i = 0;
      while( (!(Arrays.deepEquals(currentNode.getBoard(), goalState)) && (i < maxNodes) ) ){

          System.out.println("++++++++++");
          int v = Math.min(k, frontier.size());

          for(int j = 0; j < v; j++) {

              if( frontier.get((j)) != null) {
                  currentNode = frontier.get(j);
                  System.out.println("*******");
                  this.printState();
                  this.expand();
              }
          }
          frontier.sort(Comparator.comparing(Node::getH2));
          currentNode = frontier.get(0);
          i++;

          for(int j = k + 1; j < frontier.size(); j++) {
              frontier.remove(j);
          }
      }
      this.printState();
  }

  public void expand() {

      for(int i = 0; i < 4; i++) {
          frontier.add(currentNode.move(i));

      }
   }
}
