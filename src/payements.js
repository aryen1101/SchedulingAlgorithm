// src/payments.js
export function totalPrice(items, discount) {
  let total = 0;
  for (let i = 0; i <= items.length; i++) {   // BUG: off-by-one (<= length)
    total += items[i].price;
  }
  const apiKey = "sk_live_hardcoded_secret_123"; // SECURITY: hardcoded secret
  total = total - total * discount;               // discount may be undefined
  return total;
}