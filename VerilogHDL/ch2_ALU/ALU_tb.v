module ALU_tb;
  
  reg [15:0] in0, in1;
  reg [2:0] select;
  wire [15:0] out;

  initial begin
      //in0 = 128, in1 = 8;
      in0 = 16'd128;
      in1 = 16'd8;
      //add 128+8=132
      #100 select = 3'b000;
      //sub 128-8=120
      #100 select = 3'b001;
      //and 128 and 8 = 0000000000000000
      #100 select = 3'b010;
      //or 128 or 8 = 0000000010001000
      #100 select = 3'b011;
      //<< 128 << 8 = 1000000000000000
      #100 select = 3'b100;
      //>> 128 >> 8 = 0000000000000001
      #100 select = 3'b101;
      
      #100 $stop;
       
  end
  
  ALU test(
    .in0(in0),
    .in1(in1),
    .select(select),
    .out(out)
  );
  
endmodule
