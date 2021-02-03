package homework;

import java.util.*;


class MazeCell{
	int i;
	int j;
	int dir;
	public MazeCell(int _i,int _j,int _dir) {
		i=_i;
		j=_j;
		dir=_dir;
	}
	public String toString() {
		return "<"+i+","+j+">";
	}
}

public class MazePath{
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
	
	public void BreadthFirstTraversal() {
		int [][]mark=new int[maze.length][maze[0].length];
		//최단 경로를 찾기 위해 그 전에 방문했던 좌표를 기억하는 temp[][]를 만듬
		MazeCell[][] temp=new MazeCell[maze.length][];
		for(int i=0;i<maze.length;i++) {
			temp[i]=new MazeCell[maze[0].length];
		}
		mark[1][1]=1;
		//bfs를 위한 q생성
		Queue<MazeCell> q=new LinkedList<MazeCell>();
		q.offer(new MazeCell(1,1,1));
		while(!q.isEmpty()) {
			//bfs구현
			MazeCell mc=q.poll();
			for(int i=0;i<4;i++) {
				int nexti=mc.i+move[i][0];
				int nextj=mc.j+move[i][1];
				
				if(nexti==m&&nextj==n) {
					temp[nexti][nextj]=mc;
					System.out.println("BFS로 최단경로를 발견했습니다.");
					//최단경로를 저장하기 위한 stack 선언
					Stack<MazeCell> q1=new Stack<MazeCell>();
					//현재 좌표를 도착점으로 하고
					//역으로 추적
					int curi=m,curj=n;
					while(true) {
						//지금 좌표를 q1에 push함
						q1.push(new MazeCell(curi,curj,1));
						if(curi==1&&curj==1) {
							//출발지점에 도착하면 반복문 종료
							break;
						}
						//현위치를 오기 바로 직전 좌표로 이동
						MazeCell prev=temp[curi][curj];
						curi=prev.i;
						curj=prev.j;
					}
					//최단경로 출력
					while(!q1.isEmpty()) {
						System.out.println(q1.pop());
					}
					return;
				}
				//만약 현좌표에서 갈 수 있는 방향이 있으면, q에 offer,
				//다음 좌표에 해당하는 temp[nexti][nextj]에 지금좌표 mc.i,mc.j 저장
				if(nexti>=0&&nexti<maze.length && nextj>=0&&nextj<maze[0].length) {
					if((maze[nexti][nextj]==0||maze[nexti][nextj]==2)&&mark[nexti][nextj]==0) {
						q.offer(new MazeCell(nexti,nextj,i));
						mark[nexti][nextj]=mark[mc.i][mc.j]+1;
						temp[nexti][nextj]=mc;
					}
				}
			}
		}
		System.out.println("bfs로 경로를 발견하지 못했습니다.");
	}
	
	
	public void DepthFirstTraversal() {
		//스택 두개 준비
		Stack<MazeCell> st=new Stack();
		Stack<MazeCell> st1=new Stack();
		//최단 경로를 찾기 위해 갈림길 정보에 대한 스택 준비
		Stack<MazeCell> checkpoint=new Stack();
		//지나왔는지 체크하는 배열 생성
		int [][]mark=new int[maze.length][maze[0].length];
		st.push(new MazeCell(1,1,1));
		mark[1][1]=1;
		while(!st.isEmpty()) {
			MazeCell p=st.pop();
			//이동한 부분을 st1에 push
			st1.push(p);
			//갈림길인지 체크할 cnt선언
			int cnt=0;
			//현지점기준 4방향으로 이동했을때 이동가능한곳 st에 push
			for(int mi=0;mi<4;mi++) {
				int nexti=p.i+move[mi][0];
				int nextj=p.j+move[mi][1];
				//도착했을때 st1에 도착지점 push후
				if(nexti==m&&nextj==n) {
					System.out.println("DFS로 최단경로를 찾았습니다.");
					st1.push(new MazeCell(nexti,nextj,mi));
					st.clear();
					//st에 최단경로를 역순으로 push
					while(!st1.isEmpty()) {
						st.push(st1.pop());
					}
					//순서대로 출력후 종료
					while(st.isEmpty()!=true) {
						System.out.println(st.pop());
					}
					return;
				}
				//다음번 경로가 범위안에 있고, 지나오지 않았으며, 갈 수 있는 길일때
				if(nexti>=0&&nexti<maze.length && nextj>=0&&nextj<maze[0].length) {
					if((maze[nexti][nextj]==0||maze[nexti][nextj]==2)&&mark[nexti][nextj]==0) {
						//다음번 경로를 push
						st.push(new MazeCell(nexti,nextj,mi));
						//현재 지점을 지나왔다고 표시
						mark[nexti][nextj]=1;
						//현지점 기준으로 갈 수 있는 경우의수 ++
						cnt++;
					}
				}
			}
			//4개의 지점을 반복문을 이용해서 탐색한뒤
			//갈 수 있는 방향이 2개 이상일때(갈림길일때)
			if(cnt>1) {
				//현지점을 체크포인트에 push
				checkpoint.push(p);
			}
			//더이상 갈 방향이 없을때
			if(cnt==0) {
				//최단경로를 저장하는 st1 스택의 peek가 체크포인트가 나올때까지 pop
				MazeCell cp=checkpoint.pop();
				while(st1.peek()!=cp) {
					st1.pop();
				}
			}
			
		}
		//모든 경우를 돌았는데 못나온 경우
		System.out.println("dfs로 경로를 찾지 못했습니다.");
	}
	
////////////////////////////////////////////////////////////////////////////////////////	
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
					BreadthFirstTraversal();
					DepthFirstTraversal();
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
				else{
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
				 System.out.print(str[(maze[i][j])]+"\t");
			 }
			 System.out.println("");
		 }
	 }
	
	 public static void main(String[] args){
		 new MazePath();
	 }
}