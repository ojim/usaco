import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;


public class cgiving2 {
    static final int infinity = 1000000000;
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter w = new PrintWriter(System.out);

        String[] inputs = r.readLine().split(" ");
        
        int v = Integer.parseInt(inputs[0]); //number of pastures
        int e = Integer.parseInt(inputs[1]); //number of paths
        int s = 1; //barn position
        int bulls = Integer.parseInt(inputs[2]); //number of bulls
        
        int[] starts = new int[bulls]; //starting pastures of the bulls
        int[] ends = new int[bulls]; //pastures in which the bull wants to give chocolate to the cow of his dreams
        
        Vertex[] vertices = new Vertex[v + 1];
        
        for (int i = 1; i < vertices.length; i++) {
            vertices[i] = new Vertex(i);
        }
        
        for (int i = 0; i < e; i++) {
            inputs = r.readLine().split(" ");
            
            int a = Integer.parseInt(inputs[0]);
            int b = Integer.parseInt(inputs[1]);
            int c = Integer.parseInt(inputs[2]);
            vertices[a].addPath(b, c);
            vertices[b].addPath(a, c);
        }
        
        for (int i = 0; i < bulls; i++) {
            inputs = r.readLine().split(" ");
            starts[i] = Integer.parseInt(inputs[0]);
            ends[i] = Integer.parseInt(inputs[1]);
        }
        
        vertices[s].distance = 0;
        vertices[s].intree = true;
        
        int treesize = 1;
        
        for (int i = 0; i < vertices[s].adjacents.size(); i++) {
            vertices[vertices[s].adjacents.get(i)].distance = vertices[s].costs.get(i);
        }
        
        while (treesize < v) {
            Vertex closest = null;
            int closestDist = infinity;
            for (int i = 1; i < vertices.length; i++) {
                if (vertices[i].distance < closestDist && !vertices[i].intree) {
                    closest = vertices[i];
                    closestDist = closest.distance;
                }
            }
            
            treesize++;
            //cost += closestDist;
            if (closest != null)
            closest.intree = true;
            else break;
            
            for (int i = 0; i < closest.adjacents.size(); i++) {
                if (vertices[closest.adjacents.get(i)].distance > closest.costs.get(i) + closest.distance) {
                    vertices[closest.adjacents.get(i)].distance = closest.costs.get(i) + closest.distance;
                }
            }
        }
        
        for (int i = 0; i < bulls; i++) {
            w.println(vertices[starts[i]].distance + vertices[ends[i]].distance);
        }
        w.flush();
    }

    public static class Vertex {
        int number;
        ArrayList<Integer> adjacents = new ArrayList<Integer>();
        ArrayList<Integer> costs = new ArrayList<Integer>();
        int distance = infinity;
        boolean intree = false;
        
        public Vertex(int n) {
            number = n;
        }
        
        public void addPath(int to, int cost) {
            adjacents.add(to);
            costs.add(cost);
        }
        
        public String toString() {
            return number + " " + distance;
        }
    }
    
}

