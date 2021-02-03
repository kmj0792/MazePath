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
		//�ִ� ��θ� ã�� ���� �� ���� �湮�ߴ� ��ǥ�� ����ϴ� temp[][]�� ����
		MazeCell[][] temp=new MazeCell[maze.length][];
		for(int i=0;i<maze.length;i++) {
			temp[i]=new MazeCell[maze[0].length];
		}
		mark[1][1]=1;
		//bfs�� ���� q����
		Queue<MazeCell> q=new LinkedList<MazeCell>();
		q.offer(new MazeCell(1,1,1));
		while(!q.isEmpty()) {
			//bfs����
			MazeCell mc=q.poll();
			for(int i=0;i<4;i++) {
				int nexti=mc.i+move[i][0];
				int nextj=mc.j+move[i][1];
				
				if(nexti==m&&nextj==n) {
					temp[nexti][nextj]=mc;
					System.out.println("BFS�� �ִܰ�θ� �߰��߽��ϴ�.");
					//�ִܰ�θ� �����ϱ� ���� stack ����
					Stack<MazeCell> q1=new Stack<MazeCell>();
					//���� ��ǥ�� ���������� �ϰ�
					//������ ����
					int curi=m,curj=n;
					while(true) {
						//���� ��ǥ�� q1�� push��
						q1.push(new MazeCell(curi,curj,1));
						if(curi==1&&curj==1) {
							//��������� �����ϸ� �ݺ��� ����
							break;
						}
						//����ġ�� ���� �ٷ� ���� ��ǥ�� �̵�
						MazeCell prev=temp[curi][curj];
						curi=prev.i;
						curj=prev.j;
					}
					//�ִܰ�� ���
					while(!q1.isEmpty()) {
						System.out.println(q1.pop());
					}
					return;
				}
				//���� ����ǥ���� �� �� �ִ� ������ ������, q�� offer,
				//���� ��ǥ�� �ش��ϴ� temp[nexti][nextj]�� ������ǥ mc.i,mc.j ����
				if(nexti>=0&&nexti<maze.length && nextj>=0&&nextj<maze[0].length) {
					if((maze[nexti][nextj]==0||maze[nexti][nextj]==2)&&mark[nexti][nextj]==0) {
						q.offer(new MazeCell(nexti,nextj,i));
						mark[nexti][nextj]=mark[mc.i][mc.j]+1;
						temp[nexti][nextj]=mc;
					}
				}
			}
		}
		System.out.println("bfs�� ��θ� �߰����� ���߽��ϴ�.");
	}
	
	
	public void DepthFirstTraversal() {
		//���� �ΰ� �غ�
		Stack<MazeCell> st=new Stack();
		Stack<MazeCell> st1=new Stack();
		//�ִ� ��θ� ã�� ���� ������ ������ ���� ���� �غ�
		Stack<MazeCell> checkpoint=new Stack();
		//�����Դ��� üũ�ϴ� �迭 ����
		int [][]mark=new int[maze.length][maze[0].length];
		st.push(new MazeCell(1,1,1));
		mark[1][1]=1;
		while(!st.isEmpty()) {
			MazeCell p=st.pop();
			//�̵��� �κ��� st1�� push
			st1.push(p);
			//���������� üũ�� cnt����
			int cnt=0;
			//���������� 4�������� �̵������� �̵������Ѱ� st�� push
			for(int mi=0;mi<4;mi++) {
				int nexti=p.i+move[mi][0];
				int nextj=p.j+move[mi][1];
				//���������� st1�� �������� push��
				if(nexti==m&&nextj==n) {
					System.out.println("DFS�� �ִܰ�θ� ã�ҽ��ϴ�.");
					st1.push(new MazeCell(nexti,nextj,mi));
					st.clear();
					//st�� �ִܰ�θ� �������� push
					while(!st1.isEmpty()) {
						st.push(st1.pop());
					}
					//������� ����� ����
					while(st.isEmpty()!=true) {
						System.out.println(st.pop());
					}
					return;
				}
				//������ ��ΰ� �����ȿ� �ְ�, �������� �ʾ�����, �� �� �ִ� ���϶�
				if(nexti>=0&&nexti<maze.length && nextj>=0&&nextj<maze[0].length) {
					if((maze[nexti][nextj]==0||maze[nexti][nextj]==2)&&mark[nexti][nextj]==0) {
						//������ ��θ� push
						st.push(new MazeCell(nexti,nextj,mi));
						//���� ������ �����Դٰ� ǥ��
						mark[nexti][nextj]=1;
						//������ �������� �� �� �ִ� ����Ǽ� ++
						cnt++;
					}
				}
			}
			//4���� ������ �ݺ����� �̿��ؼ� Ž���ѵ�
			//�� �� �ִ� ������ 2�� �̻��϶�(�������϶�)
			if(cnt>1) {
				//�������� üũ����Ʈ�� push
				checkpoint.push(p);
			}
			//���̻� �� ������ ������
			if(cnt==0) {
				//�ִܰ�θ� �����ϴ� st1 ������ peek�� üũ����Ʈ�� ���ö����� pop
				MazeCell cp=checkpoint.pop();
				while(st1.peek()!=cp) {
					st1.pop();
				}
			}
			
		}
		//��� ��츦 ���Ҵµ� ������ ���
		System.out.println("dfs�� ��θ� ã�� ���߽��ϴ�.");
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
					System.out.println("��θ� �߰��߽��ϴ�");
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
		System.out.println("��θ� �߰����� ���߽��ϴ�");
	 }
	 
	 public void displayMaze(){
		 String[] str = {"��","��","��"};
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