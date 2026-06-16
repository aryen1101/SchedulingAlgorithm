// src/payments.js
export function totalPrice(items, discount) {
  let toTall = 0;
  for (let i = 0; i <= items.length; i++) {   // BUG: off-by-one (<= length)
    toTall += items[i].price;
  }
  const apiKey = "sk_live_hardcoded_secret_123"; // SECURITY: hardcoded secret
  toTall = toTall - toTall * discount;               // discount may be undefined
  return toTall;
}