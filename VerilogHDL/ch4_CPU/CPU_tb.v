//CPU_tb
module CPU_tb;
   //CPU
  reg clk, reset;
  wire WR, m_WR, select0, select1;
  wire [2:0] operate;
  wire [7:0] Addr;
  wire [15:0] Ins, r1, r2, Z;
  wire [15:0] out0, out1, ram_data;
  wire [15:0] imm;
  assign imm = 16'o00000 + Ins[8:0];
  always #5 clk = ~clk; //T = 10ns
    
  initial begin
    clk = 1;
    reset = 1;
    
    #10 reset = 0;
    
    #100 $stop;
  end

  CPU cpu(  //CPU
    .clk(clk), .reset(reset)
  );

  PC pc(  //pc?Addr??
    .clk(clk), .reset(reset), .pc(Addr)
  );
  
  Ins_mem mem(  //???????
    .Addr(Addr), .Ins(Ins)
  );
  
  CU cu(  //????Ins?Opcode?????alu_op?????
    .wr_en(WR), .m_wr_en(m_WR), .Ins_op(Ins[15:12]), .alu_op(operate),
    .select0(select0), .select1(select1)
  );
  
  RegFile regfile( //????????????????
    .clk(clk), .WE(WR), 
    .R_port0(Ins[11:9]), .R_port1(Ins[8:6]), .W_port(Ins[11:9]),
    .R_reg0(r1), .R_reg1(r2), .W_data(out1)
  );
  mux2_1 mux_add(
    .in0(r2), .in1(imm), .select(select0), .out(out0)
  );
  mux2_1 mux_ram(
    .in0(Z), .in1(ram_data), .select(select1), .out(out1)
  );
  ALU alu(  //???????????
    .in0(r1), .in1(out0), .alu_op(operate), .Z(Z)
  );

  RAM ram(  //ld, datapath
    .clk(clk), .m_wr_en(m_WR), .Addr(imm), .W_data(Z), .R_data(ram_data)
  );

endmodule