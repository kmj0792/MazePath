package testjava1;

import java.util.*;
import java.util.Stack;

class MazeCell{
 int i;
 int j;
 int dir;
 public MazeCell(int _i,int _j,int _dir){
  i = _i;
  j = _j;
  dir = _dir;
 }
 public String toString(){
  return "<" + i  + "," + j + ">";
 }
}

public class MazePath
{
 private int[][] maze = {
       {1,1,1,1,1,1,1,1}, //1
       {1,0,1,1,0,1,1,1}, //2
       {1,0,0,1,0,1,1,1}, //3
       {1,1,0,0,0,1,1,1}, //4
       {1,1,1,0,0,0,1,1}, //5
       {1,0,0,0,1,1,1,1}, //6
       {1,1,0,1,0,0,0,1}, //7
       {1,0,0,0,0,1,0,0}, //8
       {1,1,1,1,1,1,1,1}  //9
       };
 private int[][] move ={{-1,0},{0,1},{1,0},{0,-1}}; 
 private int m = maze.length -2;
 private int n = maze[0].length -1;

 public MazePath(){  
  int mark[][] = new int[maze.length][maze[0].length];
  Stack st = new Stack();
  Stack st1 = new Stack();  
  st.push(new MazeCell(1,1,1));  
  while(st.isEmpty()!=true){
   MazeCell mc = (MazeCell)st.pop();  
   while(mc.dir<=3){
    int nextI = mc.i + move[mc.dir][0];  
    int nextJ = mc.j + move[mc.dir][1];  
    
    if(nextI == m && nextJ ==n){  
     System.out.println("경로를 발견했습니다");
     st.push(new MazeCell(mc.i,mc.j,mc.dir)); 
     st.push(new MazeCell(nextI,nextJ,0));    
     while(st.isEmpty()!=true){
      st1.push((MazeCell)st.pop());
     }
     while(st1.isEmpty()!=true){
      mc = (MazeCell)st1.pop(); 
      System.out.println(mc);
      maze[mc.i][mc.j]=2;
     }
     displayMaze(); 
     return;
    }
    if(maze[nextI][nextJ]==0 && mark[nextI][nextJ]==0){
     mark[nextI][nextJ] = 1;
     st.push(new MazeCell(mc.i,mc.j,mc.dir));  
     //mc = new MazeCell(nextI,nextJ,0);  
     mc.i = nextI;
     mc.j = nextJ;
     mc.dir = 0;
    }
    else
    {
     mc.dir++;
    }
   }
  }
  System.out.println("경로를 발견하지 못했습니다");
  
 }
 
 public void displayMaze(){
  String[] str = {"□","■","☞"};
  for(int i = 0 ; i < maze.length ; i++){
   for(int j = 0 ; j < maze[0].length ; j++){
    System.out.print(str[(maze[i][j])]);
   }
   System.out.println("");
  }
 }

 public static void main(String[] args)
 {
  new MazePath();
 }
}