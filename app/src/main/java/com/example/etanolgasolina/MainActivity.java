package com.example.etanolgasolina;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView txtResultado;
    EditText edtGasolina;
    EditText edtEtanol;
    Button btnCalcular;
    Button btnLimpar;

    // CORREÇÃO 1: Adicionado o ponto e vírgula no final de cada uma
    double precoGasolina;
    double precoEtanol;
    double resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // CORREÇÃO 2: Removido o parêntese extra que estava no final desta linha
        txtResultado = findViewById(R.id.txtResultado);
        edtGasolina = findViewById(R.id.edtGasolina);
        edtEtanol = findViewById(R.id.edtEtanol);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // O "try" tenta executar o código normalmente
                try {

                    // 1. O app pega os textos e transforma em números
                    precoGasolina = Double.parseDouble(edtGasolina.getText().toString());
                    precoEtanol = Double.parseDouble(edtEtanol.getText().toString());

                    // ========================================================
                    // O ENCAIXE FICA EXATAMENTE AQUI (Proteção contra o zero):
                    if (precoGasolina == 0) {
                        edtGasolina.setError("O preço da gasolina não pode ser zero!");
                        return; // O 'return' joga o app para fora do clique, impedindo a divisão por zero abaixo!
                    }
                    // ========================================================

                    // 2. Se a gasolina NÃO for zero, o app continua e faz a divisão com segurança
                    resultado = precoEtanol / precoGasolina;

                    if (resultado >= 0.7) {
                        txtResultado.setText("Melhor abastecer com: Gasolina");
                    } else {
                        txtResultado.setText("Melhor abastecer com: Etanol");
                    }

                } catch (NumberFormatException e) {
                    // Se o usuário digitar letras ou deixar vazio, o Java cai aqui direto!
                    if (edtGasolina.getText().toString().isEmpty() || edtEtanol.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos! ⚠️", Toast.LENGTH_SHORT).show();
                    } else {
                        edtGasolina.setError("Digite apenas números válidos!");
                        edtEtanol.setError("Digite apenas números válidos!");
                        Toast.makeText(MainActivity.this, "Erro: O campo só aceita números! ❌", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpa os campos
                edtGasolina.setText("");
                edtEtanol.setText("");
                txtResultado.setText("Melhor abastecer com: ");

                // ADICIONE ESTA LINHA AQUI EMBAIXO:
                Toast.makeText(MainActivity.this, "Informações limpas com sucesso! ✅", Toast.LENGTH_SHORT).show();
            }
        });

    } // Fecha o método onCreate
} // Fecha a classe MainActivity