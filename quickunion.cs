using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace quikUnion
{
    // still not great. this will walk up the nodes in the "tree" but it doesn't make any effort to optimization the creation of the tree
    public class quickunion
    {
        private int[] ids;

        public quickunion(int n)
        {
            this.ids = new int[n];
            for (int i = 0; i < ids.Length; i++)
            {
                this.ids[i] = i;
            }
        }


        public Tuple<int, int> root(int i)
        {

            int debth = 0;
            if (ids[i] == i)
            {
                return new Tuple<int, int>(i, debth);
            }

            debth++;

            /*
             * if "8th item points to 99 pass 99 in to get 99ths root and so on"
             * */

            return rootHelper(ids[i] /* next level */, debth);
        }

        private Tuple<int, int> rootHelper(int i, int debth)
        {
            debth++;

            if (ids[i] == i)
            {
                return new Tuple<int, int>(i, debth);
            }

            /*
             * if "8th item points to 99 pass 99 in to get 99ths root and so on"
             * */
            return rootHelper(ids[i] /* next level */, debth);
        }

        public bool connected(int p, int q)
        {
            return root(p).Item1 == root(q).Item1;
        }

        public void union(int p, int q)
        {
            var rootP = root(p);

            // point p's root to q's root.
            // this isn't ideal we could do it on the shorter (less deep) to make searches faster. since in the worst case they go to n.
            ids[rootP.Item1] = root(q).Item1;
        }

        public void Print()
        {
            for (int i = 0; i < ids.Length; i++)
            {
                Console.WriteLine("id: " + i + " " + ids[i]);
            }
        }

        public void Metrics()
        {
            List<int> d = new List<int>();
            for (int i = 0; i < ids.Length; i++)
            {
                var ele = this.root(i);
                d.Add(ele.Item2);
            }

            Console.WriteLine("Average depth: " + d.Average());
        }
    }
}
