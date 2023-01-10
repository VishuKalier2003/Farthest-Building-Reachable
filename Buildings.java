/* You are given an integer array heights representing the heights of buildings, some bricks, and some ladders... You start your journey from building 0 and move to the next building by possibly using bricks or ladders... While moving from building i to building i+1 (0-indexed),
* If the current building's height is greater than or equal to the next building's height, you do not need a ladder or bricks...
* If the current building's height is less than the next building's height, you can either use one ladder or (h[i+1] - h[i]) bricks...
Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally...
 * Eg 1: heights = [4,12,2,7,3,18,20,3,19]         bricks = 10        ladders = 2     Output = 7  
 * Eg 2: heights = [14,3,19,13]                    bricks = 17        ladders = 0     Output = 3  
 * Eg 3: heights = [4,2,7,6,9,14,12]               bricks = 5         ladders = 1     Output = 4
 * Explanation of Third case :-
 * {Go to building 1 without using ladders nor bricks since 4 >= 2... Go to building 2 using 5 bricks. You must use either bricks or ladders because 2 < 7... Go to building 3 without using ladders nor bricks since 7 >= 6... Go to building 4 using your only ladder. You must use either bricks or ladders because 6 < 9... It is impossible to go beyond building 4 because you do not have any more bricks or ladders...}  */
import java.util.*;
public class Buildings
{
    public int MaximumBuildings(int[] buildings, int bricks, int ladders)
    {
        int jumps[] = new int[buildings.length];
        for(int i = 1; i < buildings.length; i++)
        {
            if(buildings[i-1] < buildings[i])
                jumps[i] = buildings[i] - buildings[i-1];    // Creating an array which will determine where we have to jump...
            else
                jumps[i] = 0;    // Otherwise no need to jump, set it as zero...
        }
        jumps[0] = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();   // The Queue is formed on the basis of Priority, the minimum value has the Maximum Priority...
        List<Integer> list = new ArrayList<Integer>();    // List is created to keep a check of the jump successfully encountered...
        Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();   // Hashtable used to store the index and the values of jumps required...
        for(int i = 0; i < jumps.length; i++)
        {
            if(jumps[i] != 0)
            {
                if(!table.containsKey(jumps[i]))    // If Hashtable does not contains that key...
                    table.put(jumps[i], i);      // Add the Key and value...
                queue.add(jumps[i]);    // Add the jumps to the Priority Queue...
            }
        }
        int k = 0;     // first we will use all the bricks at hand...
        while(bricks > 0)
        {
            if(queue.isEmpty())    // If the queue becomes empty, we do not need to jump...
                break;
            int head = queue.peek();    // Getting head of the queue, the minimum jump...
            bricks = bricks - head;
            if(bricks >= 0)
            {
                head = queue.poll();   // If the bricks are enough to jump...
                list.add(k, head);     // Adding the Dequeued element to the list...
                System.out.println("Bricks removed : "+head);
                k++;    // Incrementing the List index...
            }
        }
        while(ladders > 0)    // Now we will use ladders...
        {
            int head = queue.poll();
            list.add(k, head);
            k++;       // Incrementing the list index...
            System.out.println("Ladder removed : "+head);
            ladders--;
        }
        System.out.println("The Hash Table formed : "+table);
        int max_index = table.get(list.get(0));     // Assuming first value popped from the Queue to be the farthest...
        for(int i = 0; i < jumps.length; i++)
        {
            if(list.contains(jumps[i]))    // If we have encountered the jump successfully and optimally...
            {   // If the index of current is larger...
                if(table.get(jumps[i]) > max_index)
                {
                    max_index = table.get(jumps[i]);
                    table.remove(jumps[i]);     // Remove the element from the Hashtable...
                }
            }
        }
        do
        {
            max_index++;   // Incrementing the index to check if it is possible to jump further...
            if(max_index == jumps.length)
            {   // If we reached the last building...
                max_index--;
                break;
            }
            else if(jumps[max_index] != 0)
            {   // If we have to jump but we do not have resources...
                max_index--;
                break;
            }
        }while(jumps[max_index] == 0);
        return max_index;
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int h, br, lad;
        System.out.print("Enter number of buildings : ");
        h = sc.nextInt();
        int buildings[] = new int[h];
        for(int i = 0; i < buildings.length; i++)
        {
            System.out.print("Enter height of "+(i+1)+" th Building : ");
            buildings[i] = sc.nextInt();
        }
        System.out.print("Enter number of bricks provided : ");
        br = sc.nextInt();
        System.out.print("Enter the number of ladders provided : ");
        lad = sc.nextInt();
        Buildings building = new Buildings();    // Object creation...
        h = building.MaximumBuildings(buildings, br, lad);     // Function calling...
        System.out.println("The Last Building possible to reach is : "+h);
        sc.close();
    }
}

// Time Complexity  - O(n) time...
// Space Complexity - O(n+h) space...      n = space of jumps array, h = Hashtable space...

/* DEDUCTIONS :- 
 * 1. Since we are solving optimally we know exactly where we have to jump...
 * 2. We store the jumps in the Priority Queue with minimum jump value with the highest priority and greedily solve them by using bricks and ladders and keep a check of it in a Hashtable and List...
 * 3. The bricks are to be used before ladder, since ladders can be used to jump to any height once, so using ladders on lower jumps would not be optimal...
*/