#include <stm32f4xx.h>
#include <stm32f4xx_gpio.h>
#include <stm32f4xx_rcc.h>

void Delay(__IO uint32_t nCount) {
  while(nCount--) {
  }
}

/* This funcion shows how to initialize
 * the GPIO pins on GPIOD and how to configure
 * them as inputs and outputs
 */
void init_GPIO(void){

	GPIO_InitTypeDef GPIO_InitStruct;

	// Peripheral D
	RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOD, ENABLE);

	GPIO_InitStruct.GPIO_Pin = GPIO_Pin_1 | GPIO_Pin_7 | GPIO_Pin_5 | GPIO_Pin_15 | GPIO_Pin_14 | GPIO_Pin_13 | GPIO_Pin_12;
	GPIO_InitStruct.GPIO_Mode = GPIO_Mode_OUT;
	GPIO_InitStruct.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_InitStruct.GPIO_OType = GPIO_OType_PP;
	GPIO_InitStruct.GPIO_PuPd = GPIO_PuPd_NOPULL;
	GPIO_Init(GPIOD, &GPIO_InitStruct);

	// Peripheral A
	RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOA, ENABLE);

	GPIO_InitStruct.GPIO_Pin = GPIO_Pin_0;
	GPIO_InitStruct.GPIO_Mode = GPIO_Mode_IN;
	GPIO_InitStruct.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_InitStruct.GPIO_OType = GPIO_OType_PP;
	GPIO_InitStruct.GPIO_PuPd = GPIO_PuPd_DOWN;
	GPIO_Init(GPIOA, &GPIO_InitStruct);
}

int main(void){

  // initialize the GPIO pins we need
  init_GPIO();

  GPIOD->BSRRL = 0xF000; // set PD12 thru PD15
  Delay(1000000L);		 // wait a short period of time
  GPIOD->BSRRH = 0xF000; // reset PD12 thru PD15

  // this counter is used to count the number of button presses
  uint8_t i = 0;

  int pressed = 0;

  while (1){
	  // If button is not pressed;
	  if( !(GPIOA->IDR & 0x0001) ) {
		  pressed = 0;
	  }

	  // If button is pressed
	  if( GPIOA->IDR & 0x0001 && pressed == 0 ) {
		  pressed = 1;

		  if( i > 2 ) {
			  i = 0;
		  }

		  switch(i){
		  	  case 0:
		  		  GPIOD->BSRRL = 0x1000; // this sets LED1 (green)
		  		  GPIOD->BSRRH = 0x8000; // this resets LED4 (blue)

		  		  GPIO_SetBits( GPIOD, GPIO_Pin_7 );
		  		  GPIO_ResetBits( GPIOD, GPIO_Pin_5 );

		  		  break;

		  	  case 1:
		  		  GPIOD->BSRRL = 0x2000; // this sets LED4
		  		  GPIOD->BSRRH = 0x1000; // this resets LED3

				  GPIO_SetBits( GPIOD, GPIO_Pin_5 );
				  GPIO_ResetBits( GPIOD, GPIO_Pin_7 );

		  		  break;

		  	 case 2:
				  GPIOD->BSRRL = 0x8000; // this sets LED4
				  GPIOD->BSRRH = 0x2000; // this resets LED3

				  GPIO_ResetBits( GPIOD, GPIO_Pin_7 | GPIO_Pin_5 );

				  break;
		  }

		  i++;

	  }
	}
}
