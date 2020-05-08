//************************PC**************************
//PC
module PC(
  input wire clk, reset,
  output wire [7:0] pc
);

  reg [7:0] pc_init;
  initial pc_init = 8'b00000000;
  assign  pc = pc_init;
  always@(posedge clk) begin  //clk上降沿,pc自动加1
  
    if(reset == 1)
       pc_init = 0;
    else
       pc_init = pc_init + 1;
    
  end
  
endmodule


//************************Ins_mem**************************
//Ins_mem(256W = 512B)
module Ins_mem(
  input wire [7:0] Addr,  //地址8位，256W
  output reg [15:0] Ins //指令长度16位
);
 
  reg [15:0] mem[255:0]; //256个存储单元，字寻址
  
  initial begin //初始化Ins_mem，10条指令
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


//************************RegFile**************************
//RegFile,8个register
module RegFile(
  input wire WE, clk,
  input wire [2:0] R_port0, R_port1, W_port,  //2个读端口，写端口
  input wire [15:0] W_data, //写入的数据
  output reg [15:0] R_reg0, R_reg1  //读出的数据
);
  
  reg [15:0] regfile[0:7];  //8个register
  
  integer i;
  initial begin //初始化8个reg，分别为0 - 70
    for(i = 0; i < 8; i = i + 1) begin
      regfile[i] = 10*i;
    end
  end
  
  always@* begin    //read
    R_reg0 = regfile[R_port0];
    R_reg1 = regfile[R_port1];
  end
  
  always@(negedge clk) begin  //write,clk下降沿，写入
      if(WE == 1)
        regfile[W_port] <= W_data;
  end
  
endmodule


//************************RAM**************************
//RAM (1KB = 512WB)
module RAM(
  input wire clk, m_wr_en, //  可读写，读写不能同时进行
  input wire [8:0] Addr,
  input wire [15:0] W_data,
  output reg [15:0] R_data
);
  
  reg [15:0] unit[511:0];
  
  integer i;
  initial begin //???10?unit????0 - 90
    for(i = 0; i < 512; i = i + 1) begin
      unit[i] = 10*i;
    end
  end
  
  always@* begin
    if(m_wr_en == 0)
      R_data = unit[Addr];
  end
  
  always@(negedge clk) begin  //下降沿写入，1写入
    if(m_wr_en == 1)
      unit[Addr] <= W_data;
  end
  
endmodule


//************************ALU**************************
//ALU
module ALU(
  input wire [15:0] in0, in1,
  input wire [2:0] alu_op,  //??8???
  output reg [15:0] Z
);

  always@* begin
    case(alu_op)
      3'b000: Z = in0 + in1;  //add
      3'b001: Z = in0 + in1;  //iadd
      3'b010: Z = in0;  //st
      3'b011: Z = in0;  //ld, old Z
    endcase
  end 
  
endmodule


//************************CU**************************
//CU
module CU(
  input wire [3:0] Ins_op,//Opcoed
  output reg wr_en, m_wr_en, select0, select1,
  output reg [2:0] alu_op
);
 
  always@* begin
    if(Ins_op == 0) begin//Opcoed = 0, add
      wr_en = 1;
      m_wr_en = 0;
      select0 = 0;
      select1 = 0;
      alu_op = 3'b000;
    end
    else if(Ins_op == 1) begin//Opcoed = 1, iadd
      wr_en = 1;
      m_wr_en = 0;
      select0 = 1;
      select1 = 0;
      alu_op = 3'b001;
    end
    else if(Ins_op == 2) begin//Opcoed = 2, st
      wr_en = 0;
      m_wr_en = 1;
      select0 = 0;
      select1 = 0;
      alu_op = 3'b010;
    end
    else if(Ins_op == 3) begin//Opcoed = 3, ld
      wr_en = 1;
      m_wr_en = 0;
      select0 = 0;
      select1 = 1;
      alu_op = 3'b011;
    end
  end

endmodule


//************************CPU**************************
//CPU
module CPU(
  input wire clk, reset
);
 
 wire WR, m_WR, select0, select1;
  wire [2:0] operate;
  wire [7:0] Addr;
  wire [15:0] Ins, r1, r2, Z;
  wire [15:0] out0, out1, ram_data;
  wire [15:0] imm;
  assign imm = 16'o00000 + Ins[8:0];

  PC pc(  //pc与Addr相连
    .clk(clk), .reset(reset), .pc(Addr)
  );
  
  Ins_mem mem(  //地址，指令相连
    .Addr(Addr), .Ins(Ins)
  );
  
  CU cu(  //写使能，Ins的Opcode部分连线，alu_op和操作连线
    .wr_en(WR), .m_wr_en(m_WR), .Ins_op(Ins[15:12]), .alu_op(operate),
    .select0(select0), .select1(select1)
  );
  
  RegFile regfile( //指令的三个地址连线，数据端口连线
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
  ALU alu(  //操作，操作数，结果连线
    .in0(r1), .in1(out0), .alu_op(operate), .Z(Z)
  );

  RAM ram(  //ld, datapath
    .clk(clk), .m_wr_en(m_WR), .Addr(imm), .W_data(Z), .R_data(ram_data)
  );
  
endmodule


//************************mux2_1**************************
//mux2_1
module mux2_1(
  input wire [15:0] in0, in1,
  input wire select,
  output reg [15:0] out
);
  always@* begin
    case (select)
      0: out = in0;
      1: out = in1;
      default: out = 15'bx;
    endcase
  end
  
endmodule


//************************CPU_tb**************************
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

  PC pc(  //pc与Addr相连
    .clk(clk), .reset(reset), .pc(Addr)
  );
  
  Ins_mem mem(  //地址，指令相连
    .Addr(Addr), .Ins(Ins)
  );
  
  CU cu(  //写使能，Ins的Opcode部分连线，alu_op和操作连线
    .wr_en(WR), .m_wr_en(m_WR), .Ins_op(Ins[15:12]), .alu_op(operate),
    .select0(select0), .select1(select1)
  );
  
  RegFile regfile( //指令的三个地址连线，数据端口连线
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
  ALU alu(  //操作，操作数，结果连线
    .in0(r1), .in1(out0), .alu_op(operate), .Z(Z)
  );

  RAM ram(  //ld, datapath
    .clk(clk), .m_wr_en(m_WR), .Addr(imm), .W_data(Z), .R_data(ram_data)
  );

endmodule

