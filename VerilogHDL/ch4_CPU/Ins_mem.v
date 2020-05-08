//Ins_mem(256W = 512B)
module Ins_mem(
  input wire [7:0] Addr,  //??8??256W
  output reg [15:0] Ins //????16?
);
 
  reg [15:0] mem[255:0]; //256?????????
  
  initial begin //???Ins_mem?10???
      mem[0] = 16'o001200;   //add r1, r2
      mem[1] = 16'o011001;   //iadd r2, 1
      mem[2] = 16'o021002;   //st r1, 2
      mem[3] = 16'o031003;   //ld r1, 3
      mem[4] = 16'o000100;   //add r0, r1
      mem[5] = 16'o010004;   //iadd r0, 4
      mem[6] = 16'o022005;   //st r2, 5
      mem[7] = 16'o033006;   //ld r3, 6
      mem[8] = 16'o010007;   //iadd r0, 7
      mem[9] = 16'o030777;   //ld r3, 777o
  end
  
  always@* begin
    Ins = mem[Addr];
  end
  
endmodule