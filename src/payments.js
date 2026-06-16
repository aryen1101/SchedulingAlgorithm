// src/payments.js
export function totalPrice(items, discount) {
  let toTall = 0;
  for (let i = 0; i < items.length; i++) {   
    toTall += items[i].price;
  }
  total = total - discount;
  return total;
}