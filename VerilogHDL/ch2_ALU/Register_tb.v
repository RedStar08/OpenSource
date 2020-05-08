module Register_tb;
  
  reg WE, clk;
  reg [2:0] R_port0, R_port1, W_port;
  reg [15:0] Write;
  wire [15:0] Read0, Read1;
   
  initial clk = 1'b1;  
  always #5 clk = ~clk;
     
  initial begin
  
     WE = 1'b1;//write
     W_port = 3'b000;//write [#0] = 10
     Write = 16'd10;
     #20;
     W_port = 3'b001;//write [#1] = 20
     Write = 16'd20;
     #20;
     W_port = 3'b010;//write [#2] = 30
     Write = 16'd30;
     #20;
     W_port = 3'b011;//write [#3] = 40
     Write = 16'd40;
     #20;
     W_port = 3'b100;//write [#4] = 50
     Write = 16'd50;
     #20;
     W_port = 3'b101;//write [#5] = 60
     Write = 16'd60;
     #20;
     W_port = 3'b110;//write [#6] = 70
     Write = 16'd70;
     #20;
     W_port = 3'b111;//write [#7] = 80
     Write = 16'd80;
     #20;
     
     WE = 1'b0; //Read
     R_port0 = 3'b000;
     R_port1 = 3'b001;
     #20;   
     
     R_port0 = 3'b010;
     R_port1 = 3'b011;
     #20;   
     
     WE = 1'b1;//Write and Read
     W_port = 3'b000;
     Write = 16'd100;
     
     R_port0 = 3'b100;
     R_port1 = 3'b101;
     #20;
     
     W_port = 3'b001;//Write and Read
     Write = 16'd200;
     
     R_port0 = 3'b110;
     R_port1 = 3'b111;
     #20;
     
     WE = 1'b0; //Read
     R_port0 = 3'b000;
     R_port1 = 3'b001;

     #20 $stop;
       
  end
  
  Register test(
    .WE(WE),
    .clk(clk),
    .R_port0(R_port0),
    .R_port1(R_port1),
    .W_port(W_port),
    .Write(Write),
    .Read0(Read0),
    .Read1(Read1)
  );
  
endmodule
