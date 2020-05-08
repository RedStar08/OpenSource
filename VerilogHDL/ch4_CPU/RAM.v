//RAM (1KB = 512WB)
module RAM(
  input wire clk, m_wr_en, //  ????????????
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
  
  always@(negedge clk) begin  //??????1??
    if(m_wr_en == 1)
      unit[Addr] <= W_data;
  end
  
endmodule