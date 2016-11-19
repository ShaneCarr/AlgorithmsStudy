using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/*
 * the slowest. it loops though the list each time to see if it needs to point the element to q (that is p or was poitning to p).
 * */
namespace quikUnion
{
    class slowUnion
    {
        int[] ids;
        public slowUnion(int N)
        {
            this.ids = new int[N];
            for (int i = 0; i < ids.Length; i++)
            {
                ids[i] = i;
            }
        }
        public bool connected(int p, int q)
        {
            return ids[p] == ids[q];
        }

        public void union(int p, int q)
        {
            var temp = this.ids[p];

            for (int i = 0; i < ids.Length; i++)
            {
                if (this.ids[i] == temp)
                {
                    this.ids[i] = this.ids[q];
                }
            }
        }

        public void Print()
        {
            for (int i = 0; i < ids.Length; i++)
            {
                Console.WriteLine("id: " + i + " " + ids[i]);
            }
        }
    }
}
