#include "greedy.h"
#include <stdio.h>
int Greedy(int drone_num[], float w_time, float w_amount, float w_scale) {
float scale = 0;
int total_load = 0;
int occupy = 3 * drone[7].d_size.height * drone[7].d_size.width
* drone[7].d_size.length;
int pack = (7 * med[0].m_size.height
* med[0].m_size.length * med[0].m_size.width +
2 * med[1].m_size.height * med[1].m_size.length
* med[1].m_size.width +
4 * med[2].m_size.height * med[2].m_size.length
* med[2].m_size.width);
float grade = 0;
while (1) {
int n = 0; float m = 0;
for (int i = 1; i < 7; i++) {
float amount = ISO.length * ISO.width * ISO.height * 3
- occupy;
int t = drone[i].type;
amount -= bay_size[t].height * bay_size[t].width
* bay_size[t].length;
amount -= (drone[i].d_size.height * drone[i].d_size.width
* drone[i].d_size.length);
amount = amount / pack;
if (amount < 120) continue;
int num = 0;
for (int j = 0; j<8; j++) num+=drone_num[j];
float time = 1.0 *num/394;
amount = amount / (80 +amount);
float temp_scale = scale;
if (drone[i].video)
temp_scale += (1.0*drone[i].v*drone[i].t/60.0);
temp_scale = temp_scale / (256 * 52.67);
float n_grade = amount * w_amount + time * w_time +
temp_scale * w_scale;
if (n_grade > m) { m = n_grade; n = i; }
}
if (m >= grade) {
grade = m;
drone_num[n]++;
scale += 1.0*drone[n].v*drone[n].t/60.0;
int t = drone[n].type;
occupy += drone[n].d_size.height * drone[n].d_size.width
* drone[n].d_size.length;
occupy += bay_size[t].height * bay_size[t].width
* bay_size[t].length;
total_load += drone[n].load;
}
if (m < grade) { break; }
}
return (ISO.length * ISO.width * ISO.height * 3 - occupy)/pack;
}
void test(float w1, float w2, float w3) {
int drone_num[8] = {0, 0, 0, 0, 0, 0, 0, 3};
printf("%d\n", Greedy(drone_num, w1, w2, w3));
for (int i = 0; i < 8; i++)
printf("%d ",drone_num[i]);
printf("\n");
}
int main() {
float w[3] = {0.2051, 0.7167, 0.0783};
test(w[0], w[1], w[2]);
}
