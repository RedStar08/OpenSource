//RegFile,8?register
module RegFile(
  input wire WE, clk,
  input wire [2:0] R_port0, R_port1, W_port,  //2????????
  input wire [15:0] W_data, //?????
  output reg [15:0] R_reg0, R_reg1  //?????
);
  
  reg [15:0] regfile[0:7];  //8?register
  
  integer i;
  initial begin //???8?reg????0 - 70
    for(i = 0; i < 8; i = i + 1) begin
      regfile[i] = 10*i;
    end
  end
  
  always@* begin    //read
    R_reg0 = regfile[R_port0];
    R_reg1 = regfile[R_port1];
  end
  
  always@(negedge clk) begin  //write,clk??????
      if(WE == 1)
        regfile[W_port] <= W_data;
  end
  
endmodule